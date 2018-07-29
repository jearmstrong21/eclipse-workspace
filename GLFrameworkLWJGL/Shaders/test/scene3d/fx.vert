#version 410 core

layout (location=0) in vec3 vertPos;
layout (location=1) in vec2 vertUV;

out vec2 uv;

uniform mat4 proj;
uniform mat4 view;
uniform mat4 model;

void main(){
    gl_Position=proj*(view*(model*vec4(vertPos,1.0)));
//    gl_Position=vec4(vertPos,1.0);
    uv=vertUV;
}
