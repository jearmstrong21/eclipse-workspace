#version 410 core

layout (location=0) in vec2 i_pos;
layout (location=1) in vec2 i_uv ;

out vec2 uv;

void main(){
    gl_Position=vec4(i_pos,0.0,1.0);
    uv=i_uv;
}
