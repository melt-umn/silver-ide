grammar abstractsyntax;

import concretesyntax:regex;
{-- attributes for language properties. These attributes are specified once
    globally for all grammars.
--}
synthesized attribute langName :: Maybe<String>;
synthesized attribute treesitterParserName :: Maybe<String>;
synthesized attribute fileExtensions :: [String];
synthesized attribute firstLineRegex :: Maybe<RegularExpression>;
synthesized attribute grammarWideGlobalSpecs :: Spec;

nonterminal LanguageProperties with 
  treesitterParserName, langName, fileExtensions, firstLineRegex,
  grammarWideGlobalSpecs;
nonterminal LanguageProperty with 
  treesitterParserName, langName, fileExtensions, firstLineRegex,
  grammarWideGlobalSpecs;

abstract production consLanguageProp
top::LanguageProperties ::= h::LanguageProperty t::LanguageProperties
{
  top.langName = orElse(h.langName, t.langName);
  top.treesitterParserName = orElse(h.treesitterParserName, t.treesitterParserName);
  top.firstLineRegex = orElse(h.firstLineRegex, t.firstLineRegex);
  top.fileExtensions = h.fileExtensions ++ t.fileExtensions;
  top.grammarWideGlobalSpecs = consSpec(h.grammarWideGlobalSpecs, t.grammarWideGlobalSpecs);
}

abstract production nilLanguageProp
top::LanguageProperties ::=
{
  top.langName = nothing();
  top.treesitterParserName = nothing();
  top.firstLineRegex = nothing();
  top.fileExtensions = [];
  top.grammarWideGlobalSpecs = nilSpec();
}

aspect default production
top::LanguageProperty ::=
{
  top.langName = nothing();
  top.treesitterParserName = nothing();
  top.firstLineRegex = nothing();
  top.fileExtensions = [];
  top.grammarWideGlobalSpecs = nilSpec();
}

abstract production langNameProp
top::LanguageProperty ::= name::String
{
  top.langName = just(name);
}

abstract production fileExtensionsProp
top::LanguageProperty ::= extensions::[String]
{
  top.fileExtensions = extensions;
}

abstract production treesitterParserNameProp
top::LanguageProperty ::= name::String
{
  top.treesitterParserName = just(name);
}

abstract production firstLineRegexProp
top::LanguageProperty ::= r::RegularExpression
{
  top.firstLineRegex = just(r);
}

abstract production grammarWideGlobalSpec
top::LanguageProperty ::= spec::Spec
{
  top.grammarWideGlobalSpecs = spec;
}
