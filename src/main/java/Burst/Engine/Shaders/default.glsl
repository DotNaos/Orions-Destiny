#type vertex
#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoord;
layout (location=3) in float aTexID;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexCoord;
out float fTexID;

void main()
{
    fColor = aColor;
    fTexCoord = aTexCoord;

    fTexID = aTexID;

    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColor;
in vec2 fTexCoord;
in float fTexID;

uniform sampler2D uTextures[8];

out vec4 color;

void main()
{
    if (fTexID > 0.0)
    {
        int id = int(fTexID);
        color = texture(uTextures[id], fTexCoord) * fColor;
    }
    else
    {
        color = fColor;
    }

    if (color.a == 0) discard;



}