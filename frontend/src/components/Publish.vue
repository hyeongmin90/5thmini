<template>
    <div>
        <Number
            label="PublishId"
            v-model="value.publishId"
            :editMode="editMode"
        />
        <Number
            label="BookId"
            v-model="value.bookId"
            :editMode="editMode"
        />
        <Boolean
            label="IsAccept"
            v-model="value.isAccept"
            :editMode="editMode"
        />
        <String
            label="SummaryUrl"
            v-model="value.summaryUrl"
            :editMode="editMode"
        />
        <String
            label="CoverUrl"
            v-model="value.coverUrl"
            :editMode="editMode"
        />
        <String
            label="Content"
            v-model="value.content"
            :editMode="editMode"
        />
        <Date
            label="CreatedAt"
            v-model="value.createdAt"
            :editMode="editMode"
        />
        <String
            label="Category"
            v-model="value.category"
            :editMode="editMode"
        />
        <Number
            label="Cost"
            v-model="value.cost"
            :editMode="editMode"
        />
        <String
            label="Title"
            v-model="value.title"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Publish',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "publishes",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
