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

pred is_link[f : File] {
  	some f1 : File | f->f1 in link
}

pred is_linked[f : File] {
  	some f1 : File | f1->f in link
}


/* The set of files in the trash. */
sig Trash in File {}
/* The set of protected files. */
sig Protected in File {}

/* The trash is empty. */
pred inv1 {
	all f : File | f not in Trash
}

/* All files are deleted. */
pred inv2 {
	all f : File | f in Trash
}

/* Some file is deleted. */
pred inv3 {
	some f : File | f in Trash
}

/* Protected files cannot be deleted. */
pred inv4 {
  	all f : File | f in Protected implies f not in Trash
	
}

/* All unprotected files are deleted.. */
pred inv5 {
	all f : File | f not in Protected implies f in Trash
}

/* A file links to at most one file. */
pred inv6 {
	all f1,f2,f3 : File | (f1->f2 in link and f1->f3 in link) implies f2=f3
}


pred inv7 {
	all f : File | is_linked[f] implies f not in Trash
}

/* There are no links. */
pred inv8 {
	all f : File | not is_link[f]
}

/* A link does not link to another link. */
pred inv9 {
	all f1,f2 : File | f1->f2 in link implies not is_link[f2]
}

/* If a link is deleted, so is the file it links to. */
pred inv10 {
	all f1,f2 : File | f1->f2 in link and f1 in Trash implies f2 in Trash
}