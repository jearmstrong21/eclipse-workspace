#version 410 core

layout (location = 0) in vec3 inPos;
layout (location = 1) in vec2 inUV;
layout (location = 2) in vec3 inCol;

out vec2 fragUV;
out vec3 fragCol;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main(){
    vec4 pos=vec4(inPos,1.0);
    gl_Position=projection*(view*(model*pos));
    fragUV=inUV;
    fragCol=inCol;
}
