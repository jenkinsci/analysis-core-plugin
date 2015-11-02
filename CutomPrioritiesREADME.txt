This should explain how to provide your own Enum to use instead of using the Priority Enum.

Why would you want to create your own Priority Enum?
	Creating your own Priority enum allows you to map annotations to more than just the current 3 Priority values. 
	This allows you to have n number of priorities to map annotations to. This makes the plugin more useful as a reporting tool,
	rather than just a base for 'static code analysis'.

Creating your own Priority Enum :
	1. Your Enum must implement the PriorityInt interface
	2. The getCssColor() method should return the corresponding CSS color string for each of the Enum values
	3. The getTrendMessage() method should return the Enum name as a lower case string, 
		this is used for navigation when clicking on a value in the trend graph
	4. The getLocalizedString() method should return the corresponding short localized string for this enum
	5. The getLongLocalizedString() method should return the corresponding long localized string for this enum
	6. The getPriorityName() method should return the corresponding name of this enum
	
Using your custom Priority Enum :

	* You can create your own AnnotationsLabelProvider to provide custom tab labels. 
			To provide custom tab labels for your custom enum values you can override the 
			initializePriorityLabels() method and add custom labels to the priorityLabels map 
			using the enum name as the key and the value is the tab label.
	* You can use your custom label provider if you create your own DetailFactory and if you 
	* In the start() method of your Plugin class you should add your custom DetailFactory by adding it to the DetailFactory using the static 
			method addDetailBuilder() and you should set the static custom priority class on the DetailFactory
	* You can create your own TabDetail class to override the jelly files for the tabs in the report. 
	 		This also allows you to create the content of the tabs for your custom enum values
	* In your custom DetailFactory you should override the createTabDetail() method to use your custom TabDetail class
	
	* Using your own class of BuildResult you can also attach the label provider
			You should override the method getNumberOfAnnotations(), should return the number of annotations for the corresponding enum
			You should override the method initializeDeltas(), should only set the delta not the deltas for the old enum values (low, normal, high)
			You should override the method initializePriorityWarnings(), this method should be empty if using custom enum values
	
	* You can create your own AbstractProjectAction to display your results in a trend graph at the Project level, 
			if you override the createConfiguration() method you can return your own GraphConfiguration
	* If you create your own GraphConfiguration you can set a custom BuildResultGraph as the default graph to use
	

NOTE: Custom priority enums do not work with the HealthAware classes yet.