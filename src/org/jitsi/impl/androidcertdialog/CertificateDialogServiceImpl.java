/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.impl.androidcertdialog;

import net.java.sip.communicator.service.certificate.*;
import net.java.sip.communicator.util.*;

import org.jitsi.*;
import org.jitsi.android.*;

import java.security.cert.*;
import java.util.*;

/**
 * Android implementation of <tt>VerifyCertificateDialogService</tt>.
 *
 * @author Pawel Domas
 */
class CertificateDialogServiceImpl
    implements VerifyCertificateDialogService
{
    /**
     * The logger.
     */
    private static final Logger logger
            = Logger.getLogger(CertificateDialogServiceImpl.class);

    /**
     * Maps request ids to <tt>VerifyCertDialog</tt> so that they can be
     * retrieved by Android <tt>Activity</tt> or <tt>Fragments</tt>.
     */
    private Map<Long, VerifyCertDialog> requestMap
            = new HashMap<Long, VerifyCertDialog>();

    /**
     * {@inheritDoc}
     */
    @Override
    public VerifyCertificateDialog createDialog(
            Certificate[] certs, String title, String message)
    {
        if(title == null)
            title = JitsiApplication.getResString(
                        R.string.service_gui_CERT_DIALOG_TITLE);

        Long requestId = System.currentTimeMillis();

        VerifyCertDialog verifyCertDialog
                = new VerifyCertDialog(requestId, certs[0], title, message);

        requestMap.put(requestId, verifyCertDialog);

        logger.debug(hashCode()+" creating dialog: "+requestId);

        return verifyCertDialog;
    }

    /**
     * Retrieves the dialog for given <tt>requestId</tt>.
     * @param requestId dialog's request identifier assigned during dialog
     *                  creation.
     * @return the dialog for given <tt>requestId</tt>.
     */
    public VerifyCertDialog retrieveDialog(Long requestId)
    {
        logger.debug(hashCode()+" getting dialog: "+requestId);

        return requestMap.get(requestId);
    }
}
