one sig List { header: lone Node } 
sig Node { link: lone Node }
pred acyclic(){
   no List.header or some n : List.header.*link | no n.link
}
fact { !acyclic }
run acyclic for 3
