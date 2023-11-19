sig File {
	link : set File 
} 

sig Trash in File {}	


sig Protected in File {}	

/* The trash is empty. */
pred inv1 {
	all f:File | f not in Trash
}

/* All files are deleted. */
pred inv2 {

}

/* Some file is deleted. */
pred inv3 {

}

/* Protected files cannot be deleted. */
pred inv4 {

}

/* All unprotected files are deleted.. */
pred inv5 {

}

/* A file links to at most one file. */
pred inv6 {

}

/* There is no deleted link. */
pred inv7 {

}

/* There are no links. */
pred inv8 {

}

/* A link does not link to another link. */
pred inv9 {

}

/* If a link is deleted, so is the file it links to. */
pred inv10 {

}