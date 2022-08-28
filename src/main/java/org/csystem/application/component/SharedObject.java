package org.csystem.application.component;

import org.springframework.stereotype.Component;

@Component
public class SharedObject {
    private int m_val;
    private volatile boolean m_produce = true;

    public synchronized void setVal(int val) throws InterruptedException {

        while (!m_produce) {
            try {
                this.wait();
            } catch (InterruptedException ignore) {
            }
        }

        m_val = val;
        m_produce = false;
        this.notify();
    }

    public synchronized int getVal()
    {

        while (m_produce) {
            try {
                this.wait();
            } catch (InterruptedException ignore) {
            }
        }

        int val = m_val;

        m_produce = true;
        this.notify();

        return val;
    }


}

