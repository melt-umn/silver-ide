grammar abstractsyntax;

-- represents that something must be in the specified grammar to be in the wildcard
synthesized attribute requiredGrammar :: Maybe<String>;
{-- represents that something must be in the specified grammar or any subgrammar 
   to be in the wildcard --}
synthesized attribute requiredUnderGrammar :: Maybe<String>;
synthesized attribute mustBeTerminal :: Boolean;
synthesized attribute mustBeNonterminal :: Boolean;
synthesized attribute excludeRules :: ExcludeRules;
synthesized attribute excludeRule :: ExcludeRule;

nonterminal WildcardCriteria with 
  requiredUnderGrammar, requiredGrammar, mustBeTerminal, mustBeNonterminal, excludeRules;

nonterminal WildcardCriteriaElement with 
  requiredUnderGrammar, requiredGrammar, mustBeTerminal, mustBeNonterminal, excludeRules;

abstract production consWildcardCriteria
top::WildcardCriteria ::= elem::WildcardCriteriaElement rest::WildcardCriteria
{
  top.requiredGrammar = orElse(elem.requiredGrammar, rest.requiredGrammar);
  top.requiredUnderGrammar = orElse(elem.requiredUnderGrammar, rest.requiredUnderGrammar);
  top.mustBeTerminal = elem.mustBeTerminal || rest.mustBeTerminal;
  top.mustBeNonterminal = elem.mustBeNonterminal || rest.mustBeNonterminal;
  -- append exclude sets together
  top.excludeRules = appendExcludeRules(elem.excludeRules, rest.excludeRules);
}

abstract production nilWildcardCriteria
top::WildcardCriteria ::= 
{
  top.requiredGrammar = nothing();
  top.requiredUnderGrammar = nothing();
  top.mustBeTerminal = false;
  top.mustBeNonterminal = false;
  top.excludeRules = nilExcludeRules();
}

aspect default production
top::WildcardCriteriaElement ::=
{
  top.requiredGrammar = nothing();
  top.requiredUnderGrammar = nothing();
  top.mustBeTerminal = false;
  top.mustBeNonterminal = false;
  top.excludeRules = nilExcludeRules();
}

abstract production grammarWildcardCriteriaElem
top::WildcardCriteriaElement ::= gram::String
{
  top.requiredGrammar = just(gram);
}

abstract production grammarsUnderWildcardCriteriaElem
top::WildcardCriteriaElement ::= gram::String
{
  top.requiredUnderGrammar = just(gram);
}

abstract production terminalOnlyWildcardCriteriaElem
top::WildcardCriteriaElement ::=
{
  top.mustBeTerminal = true;
}

abstract production nonterminalOnlyWildcardCriteriaElem
top::WildcardCriteriaElement ::=
{
  top.mustBeNonterminal = true;
}

abstract production excludeRulesWildcardCriteriaElem
top::WildcardCriteriaElement ::= rules::ExcludeRules
{
  top.excludeRules = rules;
}
