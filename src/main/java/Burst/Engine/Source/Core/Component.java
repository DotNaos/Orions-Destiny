package Burst.Engine.Source.Core;

import Burst.Engine.Source.Core.Util.ImGuiValueManager;
import Burst.Engine.Source.Core.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Component implements ImGuiValueManager {
  protected String name = "Component";
  protected transient boolean isInitialized = false;
  private long ID = -1;
  private transient Map<String, Object> initialValues = new HashMap<>();
  private transient List<String> ignoreFields = new ArrayList<>();
  protected boolean isDirty = false;

  public Component() {
    this.ID = Util.generateHashID(this.getClass().getName());
    this.name = this.getClass().getSimpleName();
  }

  public void init() {
    if (this.initialValues == null)
    {
        this.initialValues = new HashMap<>();
    }
    if (this.ignoreFields == null)
    {
        this.ignoreFields = new ArrayList<>();
    }
    try {
      getInitialValues(this, this.ignoreFields, this.initialValues);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    isInitialized = true;
  }


  public void updateEditor(float dt) {
    if (!isInitialized) {
      isInitialized = true;
      init();
    }
  }

  public void update(float dt) {
  }

  public void imgui() {
    if (!isInitialized) {
      isInitialized = true;
      init();
    }

    ImGuiShowFields(this, this.ignoreFields, this.initialValues);
  }

  public void destroy() {
    //TODO: Destroy component
  }

  public long getID() {
    return this.ID;
  }

  public String getName() {
    return this.name;
  }

  public boolean isDirty() {
    return this.isDirty;
  }

  public void setDirty() {
    this.isDirty = true;
  }

  public void setClean() {
    this.isDirty = false;
  }
}
