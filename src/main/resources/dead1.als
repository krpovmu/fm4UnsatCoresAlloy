sig List { header: lone Node } 
sig Node { link: lone Node }
pred acyclic(){
   no List.header or some n : List.header.*link | no n.link
}
run {no List} for 3
