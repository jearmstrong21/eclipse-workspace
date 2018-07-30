#version 410 core

in vec3 pos;
in vec3 normal;

out vec4 fragColor;

uniform vec3 lightPos;
uniform vec3 diffuseColor;

uniform vec3 eyePos;
uniform vec3 specularColor;

uniform vec3 ambientColor;

uniform mat4 view;
uniform mat4 model;

void main(){
    vec3 s=-normalize(pos-lightPos);
    vec3 n=normalize((model*vec4(normal,1.0)).xyz);
    vec3 r=normalize(-s+2.0*dot(s,n)*n);
    vec3 v=normalize(eyePos-(view*model*vec4(pos,1.0)).xyz);
    
    vec3 lightDiffuse=diffuseColor*dot(s,n);
    
    float dotted=dot(r,v);
    vec3 lightSpecular=specularColor*pow(dotted,10.0);
    if(dotted<0.0)lightSpecular=vec3(0.0);
    
    vec3 light=lightDiffuse+lightSpecular+ambientColor;
    
    fragColor=vec4(light,1.0);
}
