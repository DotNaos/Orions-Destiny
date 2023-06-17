package Burst.Engine.Source.Core.Render;

import Burst.Engine.Config.Constants.Color_Config;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.Graphics.Shader;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * @author GamesWithGabe
 * A class representing a batch of sprites to be rendered.
 * The sprites are stored in a vertex array and are rendered using OpenGL.
 */
public class RenderBatch implements Comparable<RenderBatch> {
  // Vertex
  //!======
  // Pos               Color                         tex coords     tex id
  // float, float,     float, float, float, float    float, float   float
  private final int POS_SIZE = 2;
  private final int COLOR_SIZE = 4;
  private final int TEX_COORDS_SIZE = 2;
  private final int TEX_ID_SIZE = 1;
  private final int ENTITY_ID_SIZE = 1;

  private final int POS_OFFSET = 0;
  private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
  private final int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
  private final int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
  private final int ENTITY_ID_OFFSET = TEX_ID_OFFSET + TEX_ID_SIZE * Float.BYTES;
  private final int VERTEX_SIZE = 10;
  private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

  private SpriteRenderer[] sprites;
  private int numSprites;
  private boolean hasRoom;
  private float[] vertices;
  private int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};

  private List<Texture> textures;
  private int vaoID, vboID;
  private int maxBatchSize;
  private int zIndex;

  private ViewportRenderer viewportRenderer;

  public RenderBatch(int maxBatchSize, int zIndex, ViewportRenderer viewportRenderer) {
    this.viewportRenderer = viewportRenderer;

    this.zIndex = zIndex;
    this.sprites = new SpriteRenderer[maxBatchSize];
    this.maxBatchSize = maxBatchSize;

    // 4 vertices quads
    vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];

    this.numSprites = 0;
    this.hasRoom = true;
    this.textures = new ArrayList<>();
  }

  public void start() {
    // Generate and bind a Vertex Array Object
    vaoID = glGenVertexArrays();
    glBindVertexArray(vaoID);

    // Allocate space for vertices
    vboID = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vboID);
    glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

    // Create and upload indices buffer
    int eboID = glGenBuffers();
    int[] indices = generateIndices();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

    // Enable the buffer attribute pointers
    glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
    glEnableVertexAttribArray(0);

    glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
    glEnableVertexAttribArray(1);

    glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);
    glEnableVertexAttribArray(2);

    glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
    glEnableVertexAttribArray(3);

    glVertexAttribPointer(4, ENTITY_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, ENTITY_ID_OFFSET);
    glEnableVertexAttribArray(4);
  }

  public void addSprite(SpriteRenderer spr) {
    if (spr.getTexture() == null) return;

    // Get index and add renderObject
    int index = this.numSprites;
    this.sprites[index] = spr;
    this.numSprites++;

    if (!textures.contains(spr.getTexture())) {
      textures.add(spr.getTexture());
    }


    // Add properties to local vertices array
    loadVertexProperties(index);

    if (numSprites >= this.maxBatchSize) {
      this.hasRoom = false;
    }
  }

  public void render() {
    boolean rebufferData = false;
    for (int i = 0; i < numSprites; i++) {
      SpriteRenderer spr = sprites[i];
      if (spr.isDirty()) {
        if (!hasTexture(spr.getTexture())) {
          this.viewportRenderer.destroyActor(spr.actor);
          this.viewportRenderer.addActor(spr.actor);
        } else {
          loadVertexProperties(i);
          spr.setClean();
          rebufferData = true;
        }
      }


      if (spr.actor.getTransform().getZIndex() != this.zIndex) {
        destroyIfExists(spr.actor);
        this.viewportRenderer.addActorLater(spr.actor);
        i--;
      }
    }
    if (rebufferData) {
      glBindBuffer(GL_ARRAY_BUFFER, vboID);
      glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
    }

    // Use shader
    Shader shader = ViewportRenderer.getBoundShader();
    Matrix4f projectionMatrix = Window.getScene().getViewport().getProjectionMatrix();

    Matrix4f viewMatrix = Window.getScene().getViewport().getViewMatrix();

    shader.uploadMat4f("uProjection", projectionMatrix);
    shader.uploadMat4f("uView", viewMatrix);
    for (int i = 0; i < textures.size(); i++) {
      glActiveTexture(GL_TEXTURE0 + i + 1);
      textures.get(i).bind();
    }
    shader.uploadIntArray("uTextures", texSlots);

    glBindVertexArray(vaoID);
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    glDrawElements(GL_TRIANGLES, this.numSprites * 6, GL_UNSIGNED_INT, 0);

    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    glBindVertexArray(0);

    for (int i = 0; i < textures.size(); i++) {
      textures.get(i).unbind();
    }
    shader.detach();
  }

  public boolean destroyIfExists(Actor actor) {
    SpriteRenderer sprite = actor.getComponent(SpriteRenderer.class);
    for (int i = 0; i < numSprites; i++) {
      if (sprites[i] == sprite) {
        for (int j = i; j < numSprites - 1; j++) {
          sprites[j] = sprites[j + 1];
          sprites[j].setDirty();
        }
        numSprites--;
        return true;
      }
    }

    return false;
  }

  private void loadVertexProperties(int index) {
    SpriteRenderer sprite = this.sprites[index];
    PropertiesPanel propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);

    Vector4f actorColor = sprite.getColor();

    if (propertiesPanel != null) {
      if (propertiesPanel.isActiveActor(sprite.actor)) {
        actorColor = new Vector4f(Color_Config.ACTIVE_ACTOR);
      }
    }

    // Find offset within array (4 vertices per sprite)
    int offset = index * 4 * VERTEX_SIZE;

    Vector4f color = actorColor;
    Vector2f[] texCoords = sprite.getTexCoords();

    int texId = 0;
    if (sprite.getTexture() != null) {
      for (int i = 0; i < textures.size(); i++) {
        if (textures.get(i).equals(sprite.getTexture())) {
          texId = i + 1;
          break;
        }
      }
    }

    Matrix4f transformMatrix = new Matrix4f().identity();

    transformMatrix.translate(sprite.actor.getTransform().getPosition().x, sprite.actor.getTransform().getPosition().y, 0f);
    transformMatrix.rotate((float) Math.toRadians(sprite.actor.getTransform().getRotation()), 0, 0, 1);
    transformMatrix.scale(sprite.actor.getTransform().getScaledSize().x, -sprite.actor.getTransform().getScaledSize().y, 1);


    // Add vertices with the appropriate properties
    float xAdd = 0.5f;
    float yAdd = 0.5f;
    for (int i = 0; i < 4; i++) {
      if (i == 1) {
        yAdd = -0.5f;
      } else if (i == 2) {
        xAdd = -0.5f;
      } else if (i == 3) {
        yAdd = 0.5f;
      }

      Vector4f currentPos = new Vector4f(sprite.actor.getTransform().getPosition().x + (xAdd * sprite.actor.getTransform().getScaledSize().x), sprite.actor.getTransform().getPosition().y + (yAdd * sprite.actor.getTransform().getScaledSize().y), 0, 1);
      currentPos = new Vector4f(xAdd, yAdd, 0, 1).mul(transformMatrix);


      // Load position
      vertices[offset] = currentPos.x;
      vertices[offset + 1] = currentPos.y;

      // Load color
      vertices[offset + 2] = color.x;
      vertices[offset + 3] = color.y;
      vertices[offset + 4] = color.z;
      vertices[offset + 5] = color.w;

      // Load texture coordinates
      vertices[offset + 6] = texCoords[i].x;
      vertices[offset + 7] = texCoords[i].y;

      // Load texture id
      vertices[offset + 8] = texId;

      // Load entity id
      vertices[offset + 9] = sprite.actor.getID() + 1;

      offset += VERTEX_SIZE;
    }
  }

  private int[] generateIndices() {
    // 6 indices per quad (3 per triangle)
    int[] elements = new int[6 * maxBatchSize];
    for (int i = 0; i < maxBatchSize; i++) {
      loadElementIndices(elements, i);
    }

    return elements;
  }

  private void loadElementIndices(int[] elements, int index) {
    int offsetArrayIndex = 6 * index;
    int offset = 4 * index;

    // 3, 2, 0, 0, 2, 1        7, 6, 4, 4, 6, 5
    // Triangle 1
    elements[offsetArrayIndex] = offset + 3;
    elements[offsetArrayIndex + 1] = offset + 2;
    elements[offsetArrayIndex + 2] = offset + 0;

    // Triangle 2
    elements[offsetArrayIndex + 3] = offset + 0;
    elements[offsetArrayIndex + 4] = offset + 2;
    elements[offsetArrayIndex + 5] = offset + 1;
  }

  public boolean hasRoom() {
    return this.hasRoom;
  }

  public boolean hasTextureRoom() {
    return this.textures.size() < 7;
  }

  public boolean hasTexture(Texture tex) {
    return this.textures.contains(tex);
  }

  public int zIndex() {
    return this.zIndex;
  }

  @Override
  public int compareTo(RenderBatch o) {
    return Integer.compare(this.zIndex, o.zIndex());
  }
}
