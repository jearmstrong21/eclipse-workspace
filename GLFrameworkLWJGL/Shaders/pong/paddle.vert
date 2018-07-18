#version 410 core

layout (location=0) in vec3 inPos;

uniform mat4 matrix;

void main(){
    gl_Position=matrix*vec4(inPos,1.0);
}
