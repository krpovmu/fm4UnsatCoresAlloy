/* The set of files in the file system. */
sig File {
  	/* A file is potentially a link to other files. */
	link : set File
}
/* The set of files in the trash. */
sig Trash in File {}
/* The set of protected files. */
sig Protected in File {}

/* The trash is empty. */
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
  
  all f:File | f not in Protected implies f in Trash

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