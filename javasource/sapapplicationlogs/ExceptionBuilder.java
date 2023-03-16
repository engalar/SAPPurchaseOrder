package sapapplicationlogs;

import java.util.List;

class ExceptionBuilder {

	  /**
	   * Converts the message and stacktrace of a Throwable and its cause(s)
	   * (copy/paste from MxRuntime Core)
	   *
	   * @param trace the List the exception is printed to
	   * @param e     the exception to print
	   */
	  public static void buildException(List<String> trace, Throwable e) {
		StringBuilder traceLine = new StringBuilder();
		traceLine.append(e.getClass().getName()).append(": ").append(e.getMessage());
		trace.add(traceLine.toString());
	    buildStackTrace(trace, e);

	    if (e.getCause() != null) {
	      Throwable subE = e.getCause();
	      trace.add("Caused by: ");
	      buildException(trace, subE);
	    }
	  }

	  /**
	   * Converts the stacktrace of a throwable into the given List
	   * (copy/paste from MxRuntime Core)
	   *
	   * @param trace
	   * @param e
	   */
	  private static void buildStackTrace(List<String> trace, Throwable e) {
	    for (StackTraceElement element : e.getStackTrace()) {
		  StringBuilder traceLine = new StringBuilder();
		  traceLine.append("\tat ").append(element.toString());
		  trace.add(traceLine.toString());
	    }
	  }
	}
