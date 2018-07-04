#version 410 core

layout (location = 0) in vec3 inPos;
//layout (location = 1) in vec2 inUV;
layout (location = 2) in vec3 inCol;

//out vec2 fragUV;
out vec3 fragCol;

uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;

void main(){
    vec4 pos=vec4(inPos,1.0);
//    vec4 viewPos=view*pos;
    gl_Position=projection*view*model*pos/pos.w;
//    gl_Position.w=1.0;
//    fragUV=inUV;
    fragCol=inCol;
}
