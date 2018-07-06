#version 410 core

layout ( location = 0 ) in vec3 inPos;
layout ( location = 1 ) in vec3 inNormal;
layout ( location = 2 ) in vec2 inUV;

uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;

out vec3 pos;
out vec3 normal;
out vec2 uv;

void main(){
    gl_Position=projection*view*model*vec4(inPos,1.0);
    pos=inPos;
    normal=inNormal;
    uv=inUV;
}
