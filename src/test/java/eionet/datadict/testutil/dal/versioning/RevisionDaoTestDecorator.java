package eionet.datadict.testutil.dal.versioning;

import eionet.datadict.dal.versioning.RevisionDao;
import eionet.datadict.model.versioning.Revision;
import eionet.datadict.testutil.TestDecoratorBase;
import eionet.datadict.testutil.TestDecoratorHandler;

public class RevisionDaoTestDecorator extends TestDecoratorBase<RevisionDao> implements RevisionDao {

    public RevisionDaoTestDecorator(RevisionDao targetInstance, TestDecoratorHandler... handlers) {
        super(targetInstance, handlers);
    }

    @Override
    public Revision getLatestRevision() {
        return this.execute("RevisionDao.getLatestRevision()", "getLatestRevision", Revision.class);
    }
    
}
