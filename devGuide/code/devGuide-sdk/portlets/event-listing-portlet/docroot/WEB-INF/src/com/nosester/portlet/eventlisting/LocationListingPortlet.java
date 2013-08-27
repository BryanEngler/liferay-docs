package com.nosester.portlet.eventlisting;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.nosester.portlet.eventlisting.model.Location;
import com.nosester.portlet.eventlisting.service.LocationLocalServiceUtil;
import com.nosester.portlet.eventlisting.service.LocationServiceUtil;

/**
 * @author Joe Bloggs
 */
public class LocationListingPortlet extends MVCPortlet {

	public void addLocation(ActionRequest request, ActionResponse response)
			throws Exception {

		_updateLocation(request);

		sendRedirect(request, response);
	}

	public void deleteLocation(ActionRequest request, ActionResponse response)
		throws Exception {

		long locationId = ParamUtil.getLong(request, "locationId");

		LocationLocalServiceUtil.deleteLocation(locationId);

		sendRedirect(request, response);
	}
	
	public void updateLocation(ActionRequest request, ActionResponse response)
		throws Exception {

		_updateLocation(request);

		sendRedirect(request, response);
	}

	private Location _updateLocation(ActionRequest request)
			throws PortalException, SystemException {

		long locationId = (ParamUtil.getLong(request, "locationId"));
		String name = (ParamUtil.getString(request, "name"));
		String description = (ParamUtil.getString(request, "description"));
		String streetAddress = (ParamUtil.getString(request, "streetAddress"));
		String city = (ParamUtil.getString(request, "city"));
		String stateOrProvince = (ParamUtil.getString(request, "stateOrProvince"));
		String country = (ParamUtil.getString(request, "country"));
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Location.class.getName(), request);
		
		Location location = null;

		if (locationId <= 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay) request
					.getAttribute(WebKeys.THEME_DISPLAY);
			long groupId = themeDisplay.getScopeGroupId();
				
			location = LocationServiceUtil.addLocation(groupId, name,
				description, streetAddress, city, stateOrProvince, country, 
				serviceContext);

		}
		else {
			location = LocationLocalServiceUtil.getLocation(locationId);

			location = LocationLocalServiceUtil.updateLocation(locationId, name,
					description, streetAddress, city, stateOrProvince, country,
					serviceContext);
		}
		
		return location;
	}

	private static Log _log = LogFactoryUtil.getLog(LocationListingPortlet.class);

}