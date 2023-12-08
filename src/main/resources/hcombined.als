module houses

abstract sig House {
    major: one Major,
    color: one Color,
}

sig File {
    link : set File
}

sig Trash in File {}

sig Protected in File {}

one sig H1, H2, H3 extends House {}

enum Major {
    Math, Phil, CS
}

enum Color {
    Blue, Red, White
}

fact main {

    Major in House.major

    Color in House.color

    H2.major = Phil

    ((H1.color = Red and H2.major = Phil) or (H2.color = Red and H3.major = Phil))

    some h : House | h.color = Blue and h.major = CS

    H2.major = Math

}

fact eating {
    eats = Fox->Chicken + Chicken->Grain
}

pred solvePuzzle { ord/last.far = Object }

pred test1 {
    some disj F0: Farmer | some disj X0: Fox |
    some disj C0: Chicken | some disj G0: Grain |
    some disj F0, X0, C0, G0: Object |
    some disj S0, S1, S2, S3: State {
        Farmer = F0
        Fox = X0
        Chicken = C0
        Grain = G0
        Object = F0 + X0 + C0 + G0
        eats = X0->C0 + C0->G0
        State = S0 + S1 + S2 + S3
        near = S0->F0 + S0->X0 + S0->C0 + S0->G0 + S1->X0 + S2->F0 + S2->X0 + S3->X0
        far = S1->F0 + S1->G0 + S2->G0 + S3->F0 + S3->G0
        ord/first = S0
        ord/next = S0->S1 + S1->S2 + S2->S3
        crossRiver[F0 + X0 + C0 + G0, C0, none, F0 + X0]
}


run test1 for 4 expect 1
