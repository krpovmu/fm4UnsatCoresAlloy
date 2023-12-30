module houses
abstract sig House {
    major: one Major,
    color: one Color,
}
one sig H1, H2, H3 extends House {}

sig File { link: set File }
sig Trash in File {}
sig Protected in File {}

enum Major {
    Math, Phil, CS
}

enum Color {
    Blue, Red, White
}

fact {
    Major in House.major
    Color in House.color
    H2.major = Phil
    ((H1.color = Red and H2.major = Phil) or (H2.color = Red and H3.major = Phil))
    some h: House | h.color = Blue and h.major = CS
    H2.major = Math
}

pred inv1 {all f:File|f not in Trash}
pred inv2 {all f:File|f in Trash}
pred inv3 {some f:File|f in Trash}
pred inv4 {all f:File|f in Protected implies f not in Trash}
pred inv5 {all f:File|f not in Protected implies f in Trash}
pred inv6 {all x,y,z : File | (x->y in link and x->z in link) implies z=y}
//pred inv7 {all f:File | isLink[f] implies f not in Trash}
//pred isLink[f:File] {some g:File | g->f in link}
//pred inv8 {all g:File | not isLink[g]}
pred inv9 {all f,g,h:File | f->g in link implies g->h not in link}
pred inv10 {all f:Trash,g:File | f->g in link implies g in Trash}

run {}
