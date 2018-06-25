#version 410 core

layout (location = 0) in vec3 inPos;

uniform mat4 Matrix;

void main(){
    gl_Position=Matrix*vec4(inPos,1.0);
}
