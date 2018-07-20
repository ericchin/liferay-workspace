package com.liferay.content.setup.common;

import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Eric Chin
 */
public class AddDocumentType extends UpgradeProcess {

	public AddDocumentType(
		DDM ddm, DDMBeanTranslator ddmBeanTranslator,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_ddm = ddm;
		_ddmBeanTranslator = ddmBeanTranslator;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DocumentTypeUtil.addDocumentType(
			"sample-document-type.json", "Sample Document Type", _ddm,
			_ddmBeanTranslator, _dlFileEntryTypeLocalService);
	}

	private DDM _ddm;

	private DDMBeanTranslator _ddmBeanTranslator;

	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}
