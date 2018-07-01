#version 410 core

out vec4 fragColor;

uniform sampler2D tex1;
uniform sampler2D tex2;

uniform float blendTex;
uniform float blendCol;

in vec2 fragUV;
in vec3 fragCol;

void main(){
    vec3 t1=texture(tex1,fragUV).xyz;
    vec3 t2=texture(tex2,fragUV).xyz;
    vec3 t=blendTex*t1+(1.0-blendTex)*t2;
    fragColor=vec4(t*blendCol+fragCol*(1.0-blendCol),1.0);
}
