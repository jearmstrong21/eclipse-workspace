#version 410 core

out vec4 fragColor;

in vec3 fragCol;

void main(){
    fragColor=vec4(fragCol,1.0);
}
