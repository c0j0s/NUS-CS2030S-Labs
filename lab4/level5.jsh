Booking findBestBooking(Request r, Driver[] drivers) {
    PriorityQueue<Booking> pq = new PriorityQueue<Booking>();
    for(Driver d: drivers) {
        pq.offer(new Booking(d, r));
    }
    return pq.poll();
}
