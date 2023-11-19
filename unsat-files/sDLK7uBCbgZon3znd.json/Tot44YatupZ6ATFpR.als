/**
 * First-order logic revision exercises based on a simple model of a 
 * file system trash can.
 * 
 * The model has 3 unary predicates (sets), File, Trash and
 * Protected, the latter two a sub-set of File. There is a binary 
 * predicate, link, a sub-set of File x File.
 *
 * Solve the following exercises using only Alloy's first-order 
 * logic:
 *	- terms 't' are variables
 *	- atomic formulas are either term comparisons 't1 = t2' and 
 * 't1 != t2' or n-ary predicate tests 't1 -> ... -> tn in P' and 
 * 't1 -> ... -> tn not in P'
 *	- formulas are composed by 
 *		- Boolean connectives 'not', 'and', 'or' and 'implies'
 *		- quantifications 'all' and 'some' over unary predicates
 **/

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
	
  	no Trash
}

/* All files are deleted. */
pred inv2 {
	
  	File in Trash
}

/* Some file is deleted. */
pred inv3 {
	
  	
}

/* Protected files cannot be deleted. */
pred inv4 {
	
  	no Protected & Trash
}

/* All unprotected files are deleted.. */
pred inv5 {
	
  	(File - Protected) in Trash
}

/* A file links to at most one file. */
pred inv6 {
	
  	~link . link in iden
}

pred isLink[f:File]{
	some g:File | g->f in link
}

/* There is no deleted link. */
pred inv7 {
	all f:File | isLink[f] implies f not in Trash 
}

/* There are no links. */
pred inv8 {
	all f:File | not isLink[f]
}

/* A link does not link to another link. */
pred inv9 {
	all f,g,h:File | f->g in link implies g->h not in link 
}	

/* If a link is deleted, so is the file it links to. */
pred inv10 {
	all f:Trash,g:File | f->g in link implies g in Trash
}