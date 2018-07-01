#version 410 core

out vec4 fragColor;

uniform sampler2D theTexture;

in vec2 fragUV;

void main(){
    fragColor=texture(theTexture,fragUV);
}
