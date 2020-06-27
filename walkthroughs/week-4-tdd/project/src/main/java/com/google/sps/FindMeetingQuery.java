// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.Arrays;
import java.util.*;

public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    ArrayList<TimeRange> busyTime = new ArrayList<TimeRange>();
    ArrayList<TimeRange> freeTime = new ArrayList<TimeRange>();
    ArrayList<TimeRange> empty = new ArrayList<TimeRange>();

    // Get requested attendees as unmodifiable set
    Collection<String> attendees = request.getAttendees();

    // Obtain arraylist of attendees busy timeslots 
    for (Event event: events){
        for (String attendee : attendees) {               
           if(event.getAttendees().contains(attendee)){
               busyTime.add(event.getWhen());
            }       
        }
    }

    // handle no attendees
    if(busyTime.size()==0){
        // handle too long of a request
        if(request.getDuration()>TimeRange.WHOLE_DAY.duration()){
            return empty;
        }
        busyTime.add(TimeRange.fromStartDuration(0,1440));
        return busyTime;
    }

    // Find possible timeslots
    freeTime = findTimeSlots(busyTime,request.getDuration());

    return freeTime;
  }

   private ArrayList<TimeRange> findTimeSlots(ArrayList<TimeRange> eventTimes, long duration) {
    ArrayList<TimeRange> options = new ArrayList<TimeRange>();
    
    if (eventTimes.get(0).start() != 0) {
          TimeRange temp = eventTimes.get(0);
          eventTimes.add(0,temp.fromStartEnd(0,0,false));
      }

      if (eventTimes.get(eventTimes.size()-1).end() != 1440) {
          TimeRange temp = eventTimes.get(eventTimes.size()-1);
          eventTimes.add(temp.fromStartEnd(1440,1440,true));
      }

    // obtain possible timeslots 
    Collections.sort(eventTimes, TimeRange.ORDER_BY_START);
    for(int i=0;i<eventTimes.size()-1;i++){

        // handle nested events
        if((eventTimes.get(i+1).start())>=(eventTimes.get(i).start()) && (eventTimes.get(i+1).end())<=(eventTimes.get(i).end())){
            eventTimes.remove(i+1);
        }

        if(eventTimes.get(i+1).start() - eventTimes.get(i).end() >= duration){
            TimeRange temp =  eventTimes.get(i);
            int start = eventTimes.get(i).end();
            int end = eventTimes.get(i+1).start();
            options.add(temp.fromStartEnd(start,end,false));
        }
    }

    return options;
  }
  
}

