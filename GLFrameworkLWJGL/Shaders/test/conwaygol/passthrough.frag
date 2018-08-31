#version 410 core

in vec2 uv;

uniform sampler2D inTex;

uniform float dx;
uniform float dy;

layout (location=0) out vec4 fragColor;

vec4 tex(vec2 uv){
    while(uv.x<0.0)uv.x+=1.0;
    while(uv.y<0.0)uv.y+=1.0;
    while(uv.x>1.0)uv.x-=1.0;
    while(uv.y>1.0)uv.y-=1.0;
    return texture(inTex,uv);
}

void main(){
    float num=0.0;
    num+=tex( uv+vec2(-dx,-dy) ).x;
    num+=tex( uv+vec2(-dx,0.0) ).x;
    num+=tex( uv+vec2(-dx, dy) ).x;
    num+=tex( uv+vec2(0.0,-dy) ).x;
//    num+=tex( uv+vec2(0.0,0.0) ).x;
    num+=tex( uv+vec2(0.0, dy) ).x;
    num+=tex( uv+vec2( dx,-dy) ).x;
    num+=tex( uv+vec2( dx,0.0) ).x;
    num+=tex( uv+vec2( dx, dy) ).x;
    float next=0.0;
    if(texture(inTex,uv).x==1.0){
        if(num<2.0)next=0.0;
        if(num==2.0||num==3.0)next=1.0;
        else next=0.0;
    }else{
        if(num==3.0)next=1.0;
    }
    fragColor=vec4(next);
}
