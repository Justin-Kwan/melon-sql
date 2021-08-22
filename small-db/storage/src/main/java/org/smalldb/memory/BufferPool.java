package org.smalldb.memory;

import org.smalldb.page.Page;
import org.smalldb.page.PageId;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BufferPool
{
    private final Page[] pages;
    private final int pageCount;
    private final ReadWriteLock pagesLatch = new ReentrantReadWriteLock();

    public BufferPool(int pageCount)
    {
        this.pages = new Page[pageCount];
        this.pageCount = pageCount;
    }

    public Page createPage(PageId pageId)
    {
        // 0.   Make sure you call DiskManager::AllocatePage!
        // 1.   If all the pages in the buffer pool are pinned, return nullptr.
        // 2.   Pick a victim page P from either the free list or the replacer. Always pick from the free list first.
        // 3.   Update P's metadata, zero out memory and add P to the page table.
        // 4.   Set the page ID output parameter. Return a pointer to P.

        return Page.ofEmpty(pageId);
    }

    public void insertPage(Page page)
    {

    }

    interface ReplacementPolicy
    {
        // NewClockReplacer(size_t pageCount) {}

//        Victim(frame_id_t *frame_id)
//
//        Pin(frame_id_t frame_id)
//
//        Unpin(frame_id_t frame_id)
//
//        Size()
    }

    public Page readPage(PageId id)
    {
        try {
            pagesLatch.readLock().lock();

            for (Page page : pages) {
                if (page.getId().isEqualTo(id)) {
                    // pin the page? lock is just for array's safety
                    return page;
                }
            }

            return Page.ofNull();
        }
        finally {
            pagesLatch.readLock().unlock();
        }
    }

    /**
     * if page is dirty, then remove from buffer and update to disk
     * if page is not dirty, remove from buffer
     */
    public void deletePage(PageId id)
    {
        try {
            pagesLatch.writeLock().lock();

            for (Page page : pages) {
                if (id.isEqualTo(page.getId()) && page.isDirty()) {
                    // remove from buffer pool
                    // write to disk
                }
                else if (id.isEqualTo(page.getId())) {
                    // remove from disk
                }
            }
        }
        finally {
            pagesLatch.writeLock().unlock();
        }
    }
}
