# RealestateStatistics
Demo project for real-estate  statistics and houses selection. Using spring boot
<br/>
List of API methods to access 
<br/>
<table>
<tr>
<td> Fetch all data </td>
<td>http://localhost:8080/fetchData/</td>
</tr>
<tr>
<td>Sort Houses by distance from address</td>
<td>http://localhost:8080/sortByDistanceFrom/{address}</td>
</tr>
<tr>
<td>Sort Houses by number of rooms</td>
<td>http://localhost:8080/sortByRoomsGreaterThan/{roomNumber}</td>
</tr>
<tr>
<td>Fetch houses that have missing data</td>
<td>http://localhost:8080/fetchMissingHousesData/</td>
</tr>
<tr>
<td>Move to house with constrains</td>
<td>http://localhost:8080/moveToHouseWithConstrains/{address}/{roomNumber}/{price}</td>
</tr>
</table>
