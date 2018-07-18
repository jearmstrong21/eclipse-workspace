
const float PI=3.1415926536;
const float TWO_PI=6.2831853072;
const float HALF_PI=1.5707963268;

float lerp(float t,float a,float b){
    return a+(b-a)*t;
}
float norm(float t,float a,float b){
    return (t-a)/(b-a);
}
float map(float t,float e1,float s1,float e2,float s2){
    return lerp(norm(t,e1,s1),e2,s2);
}

