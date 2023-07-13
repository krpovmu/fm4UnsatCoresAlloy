module listNoDangling

one sig List { 
   header: lone Node 
} 

sig Node { 
   link: lone Node 
}

pred acyclic(){
   no List.header or some n : List.header.*link | no n.link
}

pred noDangling(){
   Node in List.header.*link
}
run {acyclic and noDangling} for 3
