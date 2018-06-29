#version 410 core

layout (location = 0) in vec3 inPos;
layout (location = 1) in vec3 inCol;

out vec3 fragCol;

void main(){
    gl_Position=vec4(inPos,1.0);
    fragCol=inCol;
}
