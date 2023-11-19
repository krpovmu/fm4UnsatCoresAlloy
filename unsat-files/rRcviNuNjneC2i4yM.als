sig File {
  	link : set File	
}	


sig Trash in File {}	


/* The set of protected files. */
sig Protected in File {} 




pred inv1 {
	all f:File | f not in Trash
}

/* All files are deleted. */
pred inv2 {
	all f:File | f in Trash
}

/* Some file is deleted. */
pred inv3 {
	some f:File | f in Trash
}

/* Protected files cannot be deleted. */
pred inv4 {
	all f:Protected | f not in Trash
}

/* All unprotected files are deleted.. */
pred inv5 {
	all f:File | (f not in Protected) implies f in Trash
}


pred inv6 {
	all x,y,z : File | (x->y in link and x->z in link) implies y=z
}

/* There is no deleted link. */
pred inv7 {
	all f:File | isLink[f] implies f not in Trash
}

pred isLink[f:File]{
	some g:File | g->f in link
}


pred isLinked[f:File]{
	some g:File | g->f in link
}

/* There are no links. */
pred inv8 {
	all f:File | not isLinked[f]
}

/* A link does not link to another link. */
pred inv9 {
	all f,g,h:File | f->g in link implies g->h not in link
}

/* If a link is deleted, so is the file it links to. */
pred inv10 {
	all f:Trash,g:File | f->g in link implies g in Trash
}