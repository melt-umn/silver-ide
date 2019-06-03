grammar abstractsyntax;

-- represents that something must be in the specified grammar to be required
synthesized attribute requiredGrammar :: Maybe<String>;
synthesized attribute mustBeTerminal :: Boolean;
synthesized attribute mustBeNonterminal :: Boolean;
synthesized attribute excludeRules :: ExcludeRules;
synthesized attribute excludeRule :: ExcludeRule;

nonterminal WildcardCriteria with 
  requiredGrammar, mustBeTerminal, mustBeNonterminal, excludeRules;

nonterminal WildcardCriteriaElement with 
  requiredGrammar, mustBeTerminal, mustBeNonterminal, excludeRules;

abstract production consWildcardCriteria
top::WildcardCriteria ::= elem::WildcardCriteriaElement rest::WildcardCriteria
{
  top.requiredGrammar = orElse(elem.requiredGrammar, rest.requiredGrammar);
  top.mustBeTerminal = elem.mustBeTerminal || rest.mustBeTerminal;
  top.mustBeNonterminal = elem.mustBeNonterminal || rest.mustBeNonterminal;
  -- append exclude sets together
  top.excludeRules = appendExcludeRules(elem.excludeRules, rest.excludeRules);
}

abstract production nilWildcardCriteria
top::WildcardCriteria ::= 
{
  top.requiredGrammar = nothing();
  top.mustBeTerminal = false;
  top.mustBeNonterminal = false;
  top.excludeRules = nilExcludeRules();
}

aspect default production
top::WildcardCriteriaElement ::=
{
  top.requiredGrammar = nothing();
  top.mustBeTerminal = false;
  top.mustBeNonterminal = false;
  top.excludeRules = nilExcludeRules();
}

abstract production grammarWildcardCriteriaElem
top::WildcardCriteriaElement ::= gram::String
{
  top.requiredGrammar = just(gram);
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
