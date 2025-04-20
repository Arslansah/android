package org.arslansah.android.ui.photoviewer;

import org.arslansah.android.model.Status;
import org.arslansah.android.ui.displayitems.MediaGridStatusDisplayItem;

public interface PhotoViewerHost{
	void openPhotoViewer(String parentID, Status status, int attachmentIndex, MediaGridStatusDisplayItem.Holder gridHolder);
}
