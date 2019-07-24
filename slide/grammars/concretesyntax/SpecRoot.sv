grammar concretesyntax;

-- given spec files, get a spec root
synthesized attribute specification :: Spec;
-- child specifications. These are used to provide specification for nonterminals
-- or terminals when they are the children of some nonterminal.
{--
synthesized attribute childSpecs :: [Spec];
inherited attribute parentName :: String;
--}
autocopy attribute grammarName :: String;
-- synthesized from the declaration specName flows down the AST
synthesized attribute specificationName :: String;
-- the declaration of what specification a file belongs to
nonterminal SpecificationDecl with specificationName, unparse;
-- a specification for a file
nonterminal SpecRoot with specification, unparse, specificationName;

nonterminal Specifications with specification, unparse;
-- a specification for a single grammar
nonterminal Specification with specification, unparse;
nonterminal SpecComponents with 
  grammarName, specification, unparse;
-- a specification for a single grammar component
nonterminal SpecComponent with 
  grammarName, specification, unparse;

concrete production specRoot
top::SpecRoot ::= decl::SpecificationDecl specs::Specifications
{
  top.specificationName = decl.specificationName;
  top.specification = specs.specification;
  top.unparse = decl.unparse ++ "\n" ++ specs.unparse;
}
concrete production nilSpecifications
top::Specifications ::=
{
  top.specification = nilSpec();
  top.unparse = "";
}

concrete production consSpecification
top::Specifications ::= spec1::Specification spec2::Specifications
{
  top.specification = consSpec(spec1.specification, spec2.specification);
  top.unparse = spec1.unparse ++ ",\n" ++ spec2.unparse;
}

concrete production globalSpecificationDcl
top::Specification ::= 'global' 'ide_specification' '{' langProps::LanguageProperties_c '}'
{
  top.specification = globalSpec(langProps.langProperties);
  top.unparse = "global ide_specification { " ++ langProps.unparse ++ "}";
}

concrete production specificationDcl
top::Specification ::= 'ide_specification' 'for' gramName::QName '{' s::SpecComponents '}'
{
  top.specification = s.specification; --cstSpecRoot(langProps.langProperties, s.spec);
  top.unparse = "ide_specification for " ++ gramName.unparse ++ "{" ++ s.unparse ++ "}";
  s.grammarName = gramName.name;
}

concrete production specificationDeclaration
top::SpecificationDecl ::= 'specification' name::Id_t ';'
{
  top.unparse = "specification " ++ name.lexeme ++ ";";
  top.specificationName = name.lexeme;
}
