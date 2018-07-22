#version 410 core

in vec2 uv;

uniform sampler2D tex;

uniform float texW;
uniform float texH;
uniform float blurRad;

out vec4 fragColor;

void main(){
    
    float num=0.0;
    vec4 sum=vec4(0.0);
    for(float x=-blurRad;x<=blurRad;x+=1.0){
        for(float y=-blurRad;y<=blurRad;y+=1.0){
            float real_x=x/texW;
            float real_y=y/texH;
            num+=1.0;
            sum+=texture(tex,uv+vec2(real_x,real_y));
        }
    }
    fragColor=sum/num;
    
}
