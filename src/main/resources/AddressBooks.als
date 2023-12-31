module AddressBook

sig Time { }
abstract sig Target { }
sig Addr extends Target { }
sig email in Addr { }
sig Name extends Target { type : one Type }

enum Type { Alias, Group }

sig Book { names: Name some -> Time, addr: Name -> Target set -> Time }

fact { all t : Time | all b : Book | b.addr.t.Target in b.names.t }
fact { all t : Time | all b : Book | (Name.(b.addr.t) & Name) in b.names.t }
fact { all t : Time | all b : Book | all n : b.names.t | some n.(b.addr.t) }
fact { all t : Time | all b : Book | no n : Name | n in n.^(b.addr.t) }
fact { all t : Time | all b : Book | all n : type.Alias | lone n.(b.addr.t) }
fact { all t : Time | all b : Book | all n : Name | lone n.(b.addr.t :> email) }

pred add [b: Book, n: Name, a: Target, t,p: Time] {
    n in b.names.t
    a not in n.(b.addr.t)
    b.addr.p = b.addr.t + n->a
    b.names.p = b.names.t
}

fun lookup [b: Book, n: Name, t: Time] : set Addr { n.^(b.addr.t) & Addr }

//check { all t : Time, b : Book, n : b.names.t | some lookup[b,n,t] } for 4 but 1 Time, 1 Book
//run { } for 4 but exactly 1 Book, exactly 1 Time
run add for 3 but 1 Book, 2 Time

