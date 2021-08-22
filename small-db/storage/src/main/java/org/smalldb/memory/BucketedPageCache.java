package org.smalldb.memory;

import org.smalldb.page.Page;
import org.smalldb.page.PageId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BucketedPageCache
{
    private final Config config;
    private final Map<Integer, BufferPool> pageBuckets;

    public BucketedPageCache(Config config)
    {
        this.config = config;
        this.pageBuckets = new ConcurrentHashMap<>();
    }

    /**
     * ConcurrentHashMap is expensive to write into, determine tradeoff of lazy bucket
     * inserts or pre-writing.
     *
     * A user could specify a huge buffer pool allocation, which does not need to be
     * created right away.
     */
    public void insert(Page page)
    {
        int bucketHashKey = calculateBucketHashKey(page.getId());

        if (!pageBuckets.containsKey(bucketHashKey)) {
            // ignore exception if already exists (race condition)
            pageBuckets.put(bucketHashKey, new BufferPool(config.getBucketCapacity()));
        }

        BufferPool bucket = pageBuckets.get(bucketHashKey);
        bucket.insertPage(page);
    }

    private int calculateBucketHashKey(PageId pageId)
    {
        return pageId.toInt() % config.getBucketCount();
    }

    interface Config
    {
        int getBucketCount();

        int getBucketCapacity();
    }
}
