
select 
    *
from 
    Revision r 
inner join (
    select 
        max(CreationDate) CreationDate
    from
        Revision
) cr
on
    r.CreationDate = cr.CreationDate
