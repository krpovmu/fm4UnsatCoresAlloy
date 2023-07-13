module DreadburyQuant

abstract sig Person {
  killed: set Person,
  hates: set Person,
  richer: set Person
}

one sig Agatha, Butler, Charles extends Person {}

pred quantEncoding {
  some x : Person | Agatha in x.killed
  all x, y : Person | y in x.killed implies (y in x.hates and y not in x.richer)
  all x : Person | x in Agatha.hates implies x not in Charles.hates
  Agatha in Agatha.hates and Charles in Agatha.hates
  all x : Person | Agatha not in x.richer implies x in Butler.hates
  all x : Person | x in Agatha.hates implies x in  Butler.hates
  all x : Person | some y : Person | y not in x.hates
}

run quantEncoding for 3