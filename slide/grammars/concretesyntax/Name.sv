grammar concretesyntax;

{--
 - Qualified names of the form 'a:b:c:d...'
 -}
synthesized attribute name :: String;
synthesized attribute names :: [String];
nonterminal QName with name, unparse;
nonterminal JarName with name, unparse;
nonterminal Name with name, unparse;
nonterminal NameList with names, unparse;
concrete production qNameId
top::QName ::= id::Name
{
  top.name = id.name;
  top.unparse = id.unparse;
}

concrete production qNameCons
top::QName ::= id::Name ':' qn::QName
{
  top.name = id.name ++ ":" ++ qn.name;
  top.unparse = id.unparse ++ ":" ++ qn.unparse;
}

concrete production jarNameSingle
top::JarName ::= n::Name
{
  top.name = n.name;
  top.unparse = n.unparse;
}

concrete production jarNameCons
top::JarName ::= n::Name '.' rest::JarName
{
  top.name = n.name ++ "." ++ rest.name;
  top.unparse = n.unparse ++ "." ++ rest.unparse;
}
{--
 - An identifier's (possibly qualified) name.
 -}

concrete production nameIdLower
top::Name ::= id::IdLower_t
{
  top.name = id.lexeme;
  top.unparse = id.lexeme;
}
concrete production nameIdUpper
top::Name ::= id::IdUpper_t
{
  top.name = id.lexeme;
  top.unparse = id.lexeme;
}

concrete production nilNameList
top::NameList ::= 
{
  top.names = [];
  top.unparse = "";
}

concrete production singleNameList
top::NameList ::= name::Name
{
  top.names = [name.unparse];
  top.unparse = name.unparse;
}

concrete production consNameList
top::NameList ::= name::Name ',' rest::NameList
{
  top.names = name.unparse :: rest.names;
  top.unparse = name.unparse ++ ", " ++ rest.unparse;
}
