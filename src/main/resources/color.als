abstract sig Color {}

one sig Red,Yellow,Green extends Color {}

sig Light {
    color: Color
}

sig Junction {
    lights : set Light
}

// This is just for realism, make sure each light belongs to exactly one junction
fact {
    Light = Junction.lights
    no x,y:Junction | x!=y and some x.lights & y.lights
}

fun count[j:Junction, c:Color] : Int {
    #{x:Light | x in j.lights and x.color=c}
}

pred mostly[j:Junction, c:Color] {
    no cc:Color | cc!=c and count[j,cc]>=count[j,c]
}

run {some j:Junction | mostly[j,Red]} for 10 Light, 2 Junction, 10 int
run {}
