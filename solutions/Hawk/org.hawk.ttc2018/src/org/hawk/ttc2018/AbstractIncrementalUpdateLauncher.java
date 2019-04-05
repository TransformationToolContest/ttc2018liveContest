package org.hawk.ttc2018;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hawk.core.IModelIndexer;
import org.hawk.localfolder.LocalFolder;
import org.hawk.ttc2018.updaters.ChangeSequenceAwareUpdater;

import SocialNetwork.SocialNetworkPackage;

public abstract class AbstractIncrementalUpdateLauncher extends AbstractLauncher {

	private static final Pattern CHANGES_FNAME = Pattern.compile("change0*([0-9]+).xmi");
	private LocalFolder localFolder;

	public AbstractIncrementalUpdateLauncher(Map<String, String> env) {
		super(env);
	}

	@Override
	protected void applyChanges(File fInitial, int changeSequenceLimit, File fChanges) throws Exception {
		localFolder.setFileFilter(filterByChangeSequenceLimit(changeSequenceLimit));
	}

	protected Function<File, Boolean> filterByChangeSequenceLimit(int changeSequenceLimit) {
		return (f) -> {
			if (AbstractLauncher.INITIAL_MODEL_FILENAME.equals(f.getName())) {
				return true;
			} else {
				Matcher matcher = CHANGES_FNAME.matcher(f.getName());
				if (matcher.matches()) {
					final int iChangeSequence = Integer.valueOf(matcher.group(1));
					return iChangeSequence <= changeSequenceLimit;
				} else {
					return f.isDirectory();
				}
			}
		};
	}

	@Override
	protected void modelLoading(final StandaloneHawk hawk) throws Throwable {
		localFolder = hawk.requestFolderIndex(changePath, filterByChangeSequenceLimit(0));
		hawk.waitForSync();

		// Need these for quickly finding by ID
		final IModelIndexer indexer = hawk.getIndexer();
		indexer.addIndexedAttribute(SocialNetworkPackage.eNS_URI, "Post", "id");
		indexer.addIndexedAttribute(SocialNetworkPackage.eNS_URI, "Comment", "id");
		indexer.addIndexedAttribute(SocialNetworkPackage.eNS_URI, "User", "id");
	}

	@Override
	protected StandaloneHawk createHawk() throws IOException {
		return new StandaloneHawk(new ChangeSequenceAwareUpdater());
	}

}