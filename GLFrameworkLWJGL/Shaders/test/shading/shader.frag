#version 410 core

in vec3 pos;
in vec3 normal;

struct Light{
    vec3 pos;
    vec3 diffuse;
    vec3 specular;
    float shininess;
    vec3 ambient;
};

uniform Light lights[10];
uniform int numLights;

out vec4 fragColor;

uniform vec3 eyePos;

uniform mat4 view;
uniform mat4 model;

void main(){
    vec3 light=vec3(0.0);
    for(int i=0;i<numLights;i++){
        vec3 s=-normalize(pos-lights[i].pos);
        vec3 n=normalize((model*vec4(normal,1.0)).xyz);
        vec3 r=normalize(-s+2.0*dot(s,n)*n);
        vec3 v=normalize(eyePos-(view*model*vec4(pos,1.0)).xyz);
        
        vec3 lightDiffuse=lights[i].diffuse*dot(s,n);
        
        float dotted=dot(r,v);
        vec3 lightSpecular=lights[i].specular*pow(dotted,lights[i].shininess);
        if(dotted<0.0)lightSpecular=vec3(0.0);
        
        light+=lightDiffuse+lightSpecular+lights[i].ambient;
    }
    
    fragColor=vec4(light,1.0);
}
