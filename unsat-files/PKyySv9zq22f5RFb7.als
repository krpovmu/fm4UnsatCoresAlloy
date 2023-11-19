sig File {
  	link : set File	
}	


sig Trash in File {}	


/* The set of protected files. */
sig Protected in File {} 




pred inv1 {
	all f:File | f not in Trash
}



pred inv2 {
	all f:File | f in Trash
}




pred inv3 {
	some f:File | f in Trash
}




pred inv4 {
	all f:Protected | f not in Trash
}


pred inv5 {
	all f:File | (f not in Protected) implies f in Trash
}





pred inv6 {
	all x,y,z : File | (x->y in link and x->z in link) implies y=z
}




/* There is no deleted link. */



pred isLink[f:File]{
	some g:File | g->f in link
}




pred inv7 {
	all f:File | isLink[f] implies f not in Trash
}


/* There are no links. */


pred isLinked[f:File]{
	some g:File | g->f in link
}


pred inv8 {
	all f:File | not isLinked[f]
}


pred inv9 {
	all f,g,h:File | f->g in link implies g->h not in link
}


pred inv10 {
	all f:Trash,g:File | f->g in link implies g in Trash
}