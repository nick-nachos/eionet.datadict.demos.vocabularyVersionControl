package eionet.datadict.dal.versioning;

import eionet.datadict.model.versioning.Revision;

public interface RevisionDao {

    Revision getLatestRevision(); 
    
}
