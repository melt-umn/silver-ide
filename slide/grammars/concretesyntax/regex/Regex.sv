grammar concretesyntax:regex;

-- This needs to be in a separate grammar from general concrete syntax because
-- we do not ignore whitespace in this grammar
import concretesyntax;
import silver:definition:regex;

synthesized attribute regexString :: String;

nonterminal RegularExpression with regexString, unparse;
terminal RegexOpenClose_t '/';

concrete production wrappedRegex
top::RegularExpression ::= '/' reg::Regex '/'
{
  top.regexString = reg.regString;
  top.unparse = "/" ++ reg.regString ++ "/";
}

disambiguate RegexChar_t, RegexOpenClose_t
{
  pluck RegexOpenClose_t;
}
-- For now, preserve existing behavior. Whitespace is allowed in regex, and ignored.
-- Escape it if you want it.
disambiguate RegexChar_t, WhiteSpace
{
  pluck WhiteSpace;
}
