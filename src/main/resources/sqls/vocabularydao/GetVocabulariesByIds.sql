
select
    v.*
from
    Vocabulary v
where
    v.Id in ( :vocabularyIds )
order by
    v.Id
